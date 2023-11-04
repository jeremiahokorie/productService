package com.productservice.core.exceptions;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 9:41 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ValidationError implements Serializable {
    private Object rejectedValue;
    private String field;
    private String objectName;
}
