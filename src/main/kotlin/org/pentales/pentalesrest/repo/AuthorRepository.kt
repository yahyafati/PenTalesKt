package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.repository.*

interface AuthorRepository : JpaRepository<Author, Long>
