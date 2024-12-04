package com.nativedevps.support.base_class.room

import androidx.room.ColumnInfo


open class BaseEntity {

    object Fields {
        const val IS_DELETED = "is_deleted"
        const val IS_ACTIVE = "is_active"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }

    @ColumnInfo(name = Fields.IS_ACTIVE)
    var isActive: Boolean = false

    @ColumnInfo(name = Fields.IS_DELETED)
    var isDeleted: Boolean = false

}