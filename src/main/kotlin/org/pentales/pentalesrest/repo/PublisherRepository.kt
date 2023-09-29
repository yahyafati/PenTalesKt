package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.repository.*

interface PublisherRepository : JpaRepository<Publisher, Long>
