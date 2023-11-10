package com.productservice.persistence.repository;


import com.productservice.core.util.Fix;
import com.productservice.dto.request.PaginationRequest;
import com.productservice.persistence.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Stephen Obi <stephen.obi@etranzact.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 2021/02/13 1:55:04 AM
 */

@SuppressWarnings({"unused", "DuplicatedCode"})
@Slf4j
@RequiredArgsConstructor
public class CustomRepository {

    protected final EntityManager em;


    public boolean isValidId(String tableName, long id) {

        String sqlQuery = "select e from " + tableName + " e where e.id = " + id;
        return em.createQuery(sqlQuery).getResultList().isEmpty();
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        String sqlQuery = "select e from  " + tableName + " e where LOWER(e." + columnName + ") = LOWER(:value)";
        return em.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }

    public boolean isUnique(Class<?> clazz, Long id, String columnName, Object value) {
        String sqlQuery = "select e from  " + clazz.getSimpleName() + " e where e.id != :id and LOWER(e." + columnName + ") = LOWER(:value)";
        return !em.createQuery(sqlQuery)
                .setParameter("id", id)
                .setParameter("value", value)
                .getResultList()
                .stream().findFirst().isPresent();
    }

    public boolean isExist(String tableName, String columnName, Object value) {
        String sqlQuery = MessageFormat.format("select e from  {0} e where LOWER(e.{1}) = LOWER(:value)", tableName, columnName);
        return !em.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }


    public Page<User> fetchUserFollowers(Map<String, Object> filterTemp, PaginationRequest page, Long userId) {
        Map<String, Object> filter = new HashMap<>(filterTemp);
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("SELECT users FROM Users users WHERE users.id in (SELECT followers.follower FROM Followers followers WHERE followers.followed = :userId) " + (filter.isEmpty() ? "" :
                "AND "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " = :" + i.getKey() + " AND"));
        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("SELECT users FROM",
                        "select count(users) from"),
                Long.class);

        TypedQuery<User> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY users.createdDate DESC", User.class);

        typeQuery.setParameter("userId", userId);
        countQuery.setParameter("userId", userId);
        filter.entrySet().stream().filter(i -> i.getValue() != null).forEach(i -> {
            typeQuery.setParameter(i.getKey(), i.getValue());
            countQuery.setParameter(i.getKey(), i.getValue());
        });

        Long contentSize = Fix.fetchSingle(countQuery);
        page.setPage(page.getPage() <= 1 ? 1 : page.getPage());
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }

    public Page<User> fetchUserGroupUsers(Map<String, Object> filterTemp, PaginationRequest page, Long groupId) {
        Map<String, Object> filter = new HashMap<>(filterTemp);
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("SELECT users FROM Users users WHERE users.id in (SELECT gu.userId FROM GroupUsers gu WHERE gu.groupId = :groupId) " + (filter.isEmpty() ? "" :
                "AND "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " = :" + i.getKey() + " AND"));
        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("SELECT users FROM",
                        "select count(users) from"),
                Long.class);

        TypedQuery<User> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY users.createdDate DESC", User.class);

        typeQuery.setParameter("groupId", groupId);
        countQuery.setParameter("groupId", groupId);
        filter.entrySet().stream().filter(i -> i.getValue() != null).forEach(i -> {
            typeQuery.setParameter(i.getKey(), i.getValue());
            countQuery.setParameter(i.getKey(), i.getValue());
        });

        Long contentSize = Fix.fetchSingle(countQuery);
        page.setPage(page.getPage() <= 1 ? 1 : page.getPage());
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }




    public Page<User> fetchAdminUsers(Map<String, Object> filterTemp, PaginationRequest page) {

        Map<String, Object> filter = new HashMap<>(filterTemp);
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("SELECT users FROM Users users WHERE users.userLevel > 1 " + (filter.isEmpty() ? "" :
                "AND "));

        filter.entrySet().stream().filter(o -> o.getValue() == null).forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " IS NULL AND"));

        filter.entrySet().stream().filter(o -> (o.getValue() != null) && !(o.getValue() instanceof Map))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " users." + i.getKey() + " = :" + i.getKey() + " AND"));

        filter.keySet().stream().filter(o -> (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    sqlQuery.set(sqlQuery.get() + " " + k + " LIKE :" + k + " OR");
                }));
        if (filter.keySet().stream().anyMatch(o -> (filter.get(o) instanceof Map))) {
            sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 2));
            sqlQuery.set(sqlQuery.get() + "AND");
        }
        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("SELECT users FROM",
                        "select count(users) from"),
                Long.class);

        TypedQuery<User> typeQuery = em.createQuery(sqlQuery.get() + " ORDER BY users.createdDate DESC", User.class);

        filter.keySet().stream().filter(o -> filter.get(o) != null && (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);

                    typeQuery.setParameter(k, value);
                    countQuery.setParameter(k, value);

                }));
        filter.entrySet().stream().filter(i -> i.getValue() != null).forEach(i -> {
            typeQuery.setParameter(i.getKey(), i.getValue());
            countQuery.setParameter(i.getKey(), i.getValue());
        });

        Long contentSize = Fix.fetchSingle(countQuery);
        page.setPage(page.getPage() <= 1 ? 1 : page.getPage());
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(Fix.fetchList(typeQuery), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }


}