package com.roaddefects.service;

/**
 * Нарушение бизнес-правила. Непроверяемое исключение:
 * Spring по умолчанию откатывает транзакцию при RuntimeException.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
