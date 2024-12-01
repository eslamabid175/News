package com.android.news.core.data.mappers

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before

import org.junit.Test

class NewsMapperKtTest {

    // run befor each test  : intialize any common resourses or set ups needed for the testCases
    // or if we have any dependencies that i need to initialize on every test case
    @Before
    fun setUp() {

    }

    // run after each test : to clean up resourses ,reset state ex : close data base
    @After
    fun tearDown() {

    }

    @Test
    fun `toNewsList this fun should map to news list correctly`() {
        //given : is when provide ur dependencies (we have) that u want to run (vars in constroctor of the function)
        val articleDto = com.android.news.core.data.utils.testArticleDto[0]
        //when : is we perform this particular run (perform the action)
        val article = articleDto.toArticle()
        //then : what we expect from our //when (what we expect from the action)(outputs)
        assertThat(article.title).isEqualTo(articleDto.title)
        assertThat(article.description).isEqualTo(articleDto.description)
        assertThat(article.content).isEqualTo("${articleDto.article_id}" +"${articleDto.content}")
        assertThat(article.articleId).isEqualTo(articleDto.article_id)
        assertThat(article.sourceName).isEqualTo(articleDto.source_name)
    }


    @Test
    fun toNewsList() {
    }

    @Test
    fun toArticle() {
    }

    @Test
    fun testToArticle() {
    }

    @Test
    fun toArticleEntity() {
    }
}