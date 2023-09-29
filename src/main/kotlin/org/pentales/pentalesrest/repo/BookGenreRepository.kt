package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.jpa.repository.*

interface BookGenreRepository : JpaRepository<BookGenre, Long>
