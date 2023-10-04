package org.pentales.pentalesrest.dto

data class AccountCredentials(var username: String = "", var password: String = "") {


    override fun toString(): String {
        return "AccountCredentials(username='$username', password='$password')"
    }
}

