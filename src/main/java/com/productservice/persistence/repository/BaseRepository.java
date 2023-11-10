package com.productservice.persistence.repository;


import com.productservice.core.util.Fix;
import com.productservice.dto.request.PaginationRequest;
import com.productservice.service.ChecksumImplementation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import org.javers.spring.annotation.JaversAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @author stephen.obi
 */

@SuppressWarnings({"unused", "unchecked", "DuplicatedCode", "CommentedOutCode"})
@Transactional
@Repository
@Slf4j
public class BaseRepository extends CustomRepository {

    public BaseRepository(EntityManager em) {
        super(em);
    }

    @JaversAuditable
    public <T> T save(T entity) {

        em.persist(entity);
        em.flush();

        return entity;
    }


    @Transactional
    public <T> void refresh(T t) {
        em.refresh(t);
    }

    @JaversAuditable
    public <T> void update(T entity) {

        em.merge(entity);
        em.flush();

    }

    @SuppressWarnings("UnusedReturnValue")
    @JaversAuditable
    public <T> T directUpdate(T entity) {
        return em.merge(entity);
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

    public int runNativeQuery(String query) {
        return em.createNativeQuery(query).executeUpdate();
    }

    @JaversAuditable
    public <T> void delete(T entity) {
        em.remove(entity);
    }

    public <T> void detach(T entity) {
        em.detach(entity);
    }

    // @JaversAuditable
    public <T> List<T> saveAll(Iterable<T> entities) {

        List<T> result = new ArrayList<>();

        for (T entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    public <T> T untrackedSave(T entity) {
        em.persist(entity);
        return entity;
    }

    private <T> void validate(Class<T> type, T obj) {
        if (ChecksumImplementation.class.isAssignableFrom(type)) {
            try {
                ChecksumImplementation.class.getMethod("validate").invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
    public <T> T findOne(Class<T> type, Long id) {
        T obj = em.find(type, id);
        if (!ObjectUtils.isEmpty(obj)) {
            validate(type, obj);
            em.detach(obj);
            return obj;
        }
        return null;
    }

    public <T> Optional<T> findOneOptional(Class<T> type, Long id) {
        T obj = em.find(type, id);

        if (!ObjectUtils.isEmpty(obj)) {
            validate(type, obj);
            em.detach(obj);
            return Optional.of(obj);
        }
        return Optional.empty();
    }

    public <T> List<T> findAllById(Class<T> type, Iterable<T> ids) {
        TypedQuery<T> typeQuery = em.createQuery("SELECT k FROM " + type.getSimpleName() + " k WHERE k.id in :Ids",
                type);
        typeQuery.setParameter("Ids", ids);

        return Fix.fetchList(typeQuery);
    }

    public <T, K> List<T> findAllById(Class<T> type, Set<K> ids) {
        TypedQuery<T> typeQuery = em.createQuery("SELECT k FROM " + type.getSimpleName() + " k WHERE k.id in :Ids",
                type);
        typeQuery.setParameter("Ids", ids);
        return Fix.fetchList(typeQuery);
    }

//    public <T> List<T> findAll(Class<T> type) {
//        TypedQuery<T> typeQuery = em.createQuery(
//                "SELECT k FROM " + type.getSimpleName() + " k WHERE k.status =:enabled OR k.status =:pendingUpdate",
//                type);
//        typeQuery.setParameter("pendingUpdate", Status.PENDING_UPDATE_ENABLED.getValue());
//        typeQuery.setParameter("enabled", Status.ENABLED.getValue());
//        return Fix.fetchList(typeQuery);
//    }

    public <T> List<T> findAllBy(Class<T> type, String columnName, Object value) {
        String sqlQuery = "select e from  " + type.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:value)";
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, type).setParameter("value", value);

        return Fix.fetchList(typeQuery);
    }

    public <T, V, K> List<T> findAllBy(Class<T> type, V columnName, K value) {
        String sqlQuery = "select e from  " + type.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:value)";
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, type).setParameter("value", value);
        return Fix.fetchList(typeQuery);
    }

    public <T, V, K> T findOneBy(Class<T> type, V columnName, K value) {
        String sqlQuery = "select e from  " + type.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:value)";
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, type).setParameter("value", value);

        return Fix.fetchList(typeQuery).stream().findFirst().orElse(null);

    }

    public <T, V, K> Optional<T> findOneByOptional(Class<T> type, V columnName, K param) {
        String sqlQuery = "select e from  " + type.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:param)";

        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, type).setParameter("param", param);
        Optional<T> result = typeQuery.getResultList().stream().findFirst();
        result.ifPresent(t -> validate(type, t));

        return result;
    }

    public <T> Optional<T> findOneByOptional(Class<T> type, Map<String, Object> filter) {

        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + type.getSimpleName() + " e where");

        filter.keySet().forEach(i -> sqlQuery.set(sqlQuery.get() + " e." + i + " = :" + i + " AND"));
        sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get(), type);

        filter.forEach(typeQuery::setParameter);

        Optional<T> result = typeQuery.getResultList().stream().findFirst();
        result.ifPresent(t -> validate(type, t));

        return result;
    }

    public <T> Page<T> findAllByOr(Class<T> type, Map<String, Object> filter, PaginationRequest page) {
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + type.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));

        filter.keySet().forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i + " = :" + i + " OR"));

        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 3));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY createdDate", type);

        filter.forEach(typeQuery::setParameter);
        filter.forEach(countQuery::setParameter);

        Long contentSize = Fix.fetchSingle(countQuery);
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }

    //The filter map object takes care of both OR, AND & LIKE;
    //If the value is of type List means it represents OR;
    //If the value is of type Map means it represents LIKE;
    //If the value is of type String[] means it represents in;
    //By default all value are chained with AND
    //If the value is of type Pair<Date,Date> means it represents in;
    //If value is of type AtomicReference means it represents !=
    @SuppressWarnings("unchecked")
    public <T> Page<T> findAllBy(Class<T> type, Map<String, Object> filterTemp, PaginationRequest page) {

        Map<String, Object> filter = new HashMap<>(filterTemp);
//        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//          .anyMatch(c -> c.getAuthority().equals("ROLE_02761"))) {
//      filter.put("status", Status.ENABLED.getValue());
// }

        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + type.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null && !(o.getValue() instanceof List<?>)
                        && !(o.getValue() instanceof Map) && !(o.getValue() instanceof AtomicReference<?>) && !(o.getValue() instanceof String[])
                        && !(o.getValue() instanceof Pair)))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " = :" + i.getKey() + " AND"));

        filter.entrySet().stream().filter(k -> k.getValue() instanceof String[]).forEach(m ->
                sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " in :" + m.getKey() + " AND"));

        filter.entrySet().stream().filter(k -> k.getValue() instanceof Pair).forEach(m -> sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " BETWEEN :" + m.getKey() + "Start AND :" + m.getKey() + "End AND"));

        filter.entrySet().stream().filter(k -> k.getValue() instanceof AtomicReference).forEach(m ->
        {
            if (Objects.equals(m.getValue(), "null")) {
                sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " is not :" + m.getKey() + " AND");

            } else {
                sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " != :" + m.getKey() + " AND");
            }
        });

        filter.keySet().stream().filter(o -> (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    sqlQuery.set(sqlQuery.get() + " " + k + " LIKE :" + k + " OR");
                }));
        if (filter.keySet().stream().anyMatch(o -> (filter.get(o) instanceof Map))) {
            sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 2));
            sqlQuery.set(sqlQuery.get() + "AND");
        }


        filter.keySet().stream().filter(s -> (filter.get(s) != null && filter.get(s) instanceof List<?>)).forEach(s -> buildQueryString(filter, sqlQuery, s));

        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY createdDate DESC", type);

        filter.entrySet().stream().filter(i -> i.getValue() != null && !(i.getValue() instanceof List<?>) && !(i.getValue() instanceof Map)
                && !(i.getValue() instanceof AtomicReference<?>) && !(i.getValue() instanceof String[]) && !(i.getValue() instanceof Pair)).forEach(i -> {
            typeQuery.setParameter(i.getKey(), i.getValue());
            countQuery.setParameter(i.getKey(), i.getValue());
        });
        filter.entrySet().stream().filter(k -> k.getValue() instanceof String[]).forEach(m -> {
            List<String> value = Arrays.asList((String[]) m.getValue());
            typeQuery.setParameter(m.getKey(), value);
            countQuery.setParameter(m.getKey(), value);
        });

        filter.entrySet().stream().filter(k -> k.getValue() instanceof Pair).forEach(m -> {

            Pair<Date, Date> value = (Pair<Date, Date>) m.getValue();
            typeQuery.setParameter(m.getKey() + "Start", value.getKey());
            typeQuery.setParameter(m.getKey() + "End", value.getValue());
            countQuery.setParameter(m.getKey() + "Start", value.getKey());
            countQuery.setParameter(m.getKey() + "End", value.getValue());
        });

        filter.entrySet().stream().filter(i -> i.getValue() != null && (i.getValue() instanceof AtomicReference<?>)).forEach(h -> {
            typeQuery.setParameter(h.getKey(), ((AtomicReference<?>) h.getValue()).get());
            countQuery.setParameter(h.getKey(), ((AtomicReference<?>) h.getValue()).get());
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof List<?>)).forEach(i -> {
            List<?> values = (List<?>) filter.get(i);

            IntStream.range(0, values.size()).forEach(index -> {
                typeQuery.setParameter(i + "" + index, values.get(index));
                countQuery.setParameter(i + "" + index, values.get(index));
            });
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);

                    typeQuery.setParameter(k, value);
                    countQuery.setParameter(k, value);

                }));

        Long contentSize = Fix.fetchSingle(countQuery);
        page.setPage(page.getPage() <= 1 ? 1 : page.getPage());
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }

    private void buildQueryString(Map<String, Object> filter, AtomicReference<String> sqlQuery, String s) {
        sqlQuery.set(sqlQuery.get() + "(");
        IntStream.range(0, ((List<?>) filter.get(s)).size()).forEach(index -> sqlQuery.set(sqlQuery.get() + " " + s + " = :" + s + "" + index + " OR"));
        sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 3) + ") AND");
    }


    //The filter map object takes care of both OR, AND & LIKE;
    //If the value is of type List means it represents OR;
    //If the value is of type Map means it represents LIKE;
    //If the value is of type String[] means it represents in;
    //If the value is of type Pair<Date,Date> means it represents in;
    //By default, all value are chained with AND
    //If value is of type AtomicReference means it represents !=
    @SuppressWarnings("unchecked")
    public <T> List<T> findAllBy(Class<T> type, Map<String, Object> filterTemp) {

        Map<String, Object> filter = new HashMap<>(filterTemp);
//        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(c -> c.getAuthority().equals("ROLE_02761"))) {
//            filter.put("status", Status.ENABLED.getValue());
//        }

        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + type.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null && !(o.getValue() instanceof List<?>)
                        && !(o.getValue() instanceof Map) && !(o.getValue() instanceof AtomicReference<?>) && !(o.getValue() instanceof String[])
                        && !(o.getValue() instanceof Pair)))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " = :" + i.getKey() + " AND"));

        filter.entrySet().stream().filter(k -> k.getValue() instanceof String[]).forEach(m ->
                sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " in :" + m.getKey() + " AND"));


        filter.entrySet().stream().filter(k -> k.getValue() instanceof Pair).forEach(m -> sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " BETWEEN :" + m.getKey() + "Start AND :" + m.getKey() + "End AND"));

        filter.entrySet().stream().filter(k -> k.getValue() instanceof AtomicReference).forEach(m -> sqlQuery.set(sqlQuery.get() + " " + m.getKey() + " != :" + m.getKey() + " AND"));

        buildQueryStringWithLike(filter, sqlQuery);

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY createdDate DESC", type);

        filter.entrySet().stream().filter(i -> i.getValue() != null && !(i.getValue() instanceof List<?>) && !(i.getValue() instanceof Map)
                && !(i.getValue() instanceof AtomicReference<?>) && !(i.getValue() instanceof String[]) && !(i.getValue() instanceof Pair)).forEach(i -> {
            typeQuery.setParameter(i.getKey(), i.getValue());
            countQuery.setParameter(i.getKey(), i.getValue());
        });
        filter.entrySet().stream().filter(k -> k.getValue() instanceof String[]).forEach(m -> {
            List<String> value = Arrays.asList((String[]) m.getValue());
            typeQuery.setParameter(m.getKey(), value);
            countQuery.setParameter(m.getKey(), value);
        });

        filter.entrySet().stream().filter(k -> k.getValue() instanceof Pair).forEach(m -> {

            Pair<Date, Date> value = (Pair<Date, Date>) m.getValue();
            typeQuery.setParameter(m.getKey() + "Start", value.getKey());
            typeQuery.setParameter(m.getKey() + "End", value.getValue());
            countQuery.setParameter(m.getKey() + "Start", value.getKey());
            countQuery.setParameter(m.getKey() + "End", value.getValue());
        });

        filter.entrySet().stream().filter(i -> i.getValue() != null && (i.getValue() instanceof AtomicReference<?>)).forEach(h -> {
            typeQuery.setParameter(h.getKey(), ((AtomicReference<?>) h.getValue()).get());
            countQuery.setParameter(h.getKey(), ((AtomicReference<?>) h.getValue()).get());
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof List<?>)).forEach(i -> {
            List<?> values = (List<?>) filter.get(i);

            IntStream.range(0, values.size()).forEach(index -> {
                typeQuery.setParameter(i + "" + index, values.get(index));
                countQuery.setParameter(i + "" + index, values.get(index));
            });
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);

                    typeQuery.setParameter(k, value);
                    countQuery.setParameter(k, value);

                }));

        //  Long contentSize = Fix.fetchSingle(countQuery);
//        page.setPage(page.getPage() < 1 ? 1 : page.getPage());
//        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

//        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return Fix.fetchList(typeQuery);

//                new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
//                contentSize);
    }

    private void buildQueryStringWithLike(Map<String, Object> filter, AtomicReference<String> sqlQuery) {
        filter.keySet().stream().filter(o -> (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    sqlQuery.set(sqlQuery.get() + " " + k + " LIKE :" + k + " AND");
                }));


        for (String s : filter.keySet()) {
            if ((filter.get(s) != null && filter.get(s) instanceof List<?>)) {
                buildQueryString(filter, sqlQuery, s);
            }
        }

        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));
    }


    public <T> void dynamicUpdate(Class<T> type, Map<String, Object> updates, Map<String, Object> conditions) {
        StringBuilder query = new StringBuilder("UPDATE " + type.getSimpleName() + " e SET");

        updates.forEach((key, value) -> query.append(" e.").append(key).append(" =: ").append(key).append(", "));

        query.setLength(query.length() - 2);
        query.append(conditions != null && !conditions.isEmpty() ? " WHERE " : "");

        Objects.requireNonNull(conditions).forEach((key1, value1) -> query.append(" e.").append(key1).append(" =: ").append(key1).append("Update").append(" AND "));
        query.setLength(!conditions.isEmpty() ? query.length() - 4 : query.length());


        Query typeQuery = em.createQuery(query.toString());

        updates.forEach(typeQuery::setParameter);

        conditions.forEach((key, value) -> typeQuery.setParameter(key + "Update", value));

        typeQuery.executeUpdate();
    }

    public <T> int dynamicDelete(Class<T> type, Map<String, Object> conditions) {
        StringBuilder query = new StringBuilder("DELETE " + type.getSimpleName() + " WHERE ");

        conditions.forEach((key1, value1) -> query.append(key1).append(" =: ").append(key1).append(" AND "));
        query.setLength(!conditions.isEmpty() ? query.length() - 4 : query.length());

        Query typeQuery = em.createQuery(query.toString());


        conditions.forEach(typeQuery::setParameter);

        return typeQuery.executeUpdate();
    }

    public <T> long findAllByCount(Class<T> type, Map<String, Object> filterTemp) {

        Map<String, Object> filter = new HashMap<>(filterTemp);
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select count(e) from  " + type.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null && !(o.getValue() instanceof List<?>) && !(o.getValue() instanceof Map)))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i.getKey() + " = :" + i.getKey() + " AND"));

        buildQueryStringWithLike(filter, sqlQuery);

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get(), Long.class);

        filter.keySet().stream().filter(i -> filter.get(i) != null && !(filter.get(i) instanceof List<?>) && !(filter.get(i) instanceof Map)).forEach(i -> countQuery.setParameter(i, filter.get(i)));

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof List<?>)).forEach(i -> {
            List<?> values = (List<?>) filter.get(i);

            IntStream.range(0, values.size()).forEach(index -> countQuery.setParameter(i + "" + index, values.get(index)));
        });

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    countQuery.setParameter(k, value);

                }));

        return Fix.fetchSingle(countQuery);
    }
}