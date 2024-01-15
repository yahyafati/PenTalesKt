package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface BookRepository : IRepoSpecification<Book, Long>
