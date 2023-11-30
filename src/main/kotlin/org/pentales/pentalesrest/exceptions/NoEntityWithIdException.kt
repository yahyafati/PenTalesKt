package org.pentales.pentalesrest.exceptions

class NoEntityWithIdException(message: String?) : RuntimeException(message) {

    companion object {

        fun create(entityName: String, id: Long): NoEntityWithIdException {
            return NoEntityWithIdException("$entityName with id $id does not exist")
        }

        fun create(entityName: String, id: String): NoEntityWithIdException {
            return NoEntityWithIdException("$entityName with id $id does not exist")
        }
    }
}
