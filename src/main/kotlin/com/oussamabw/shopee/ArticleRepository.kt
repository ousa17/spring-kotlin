package com.oussamabw.shopee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long>