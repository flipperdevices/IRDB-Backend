package com.flipperdevices.ifrmvp.backend.db.signal.exception

sealed class TableDaoException(message: String) : Throwable(message) {
    class CategoryNotFound(id: Long) : TableDaoException("Category with id $id not found!")
    class CategoryMeta(id: Long) : TableDaoException("Category meta for category with id $id not found!")
    class BrandNotFound(id: Long) : TableDaoException("Brand with id $id not found!")
    class IrFileNotFound(id: Long) : TableDaoException("IR file with id $id not found!")
}
