package com.legomin.clientservice.domain.exception;

/**
 * The separate exception class for convenient db error handling by repository methods consumers
 */
public class DbException extends RuntimeException {

  public DbException(String message) {
    super(message);
  }
}
