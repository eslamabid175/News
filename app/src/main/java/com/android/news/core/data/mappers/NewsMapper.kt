package com.android.news.core.data.mappers

import com.android.news.core.data.local.ArticleEntity
import com.android.news.core.data.remote.ArticleDto
import com.android.news.core.data.remote.NewsListDto
import com.android.news.core.domain.models.Article
import com.android.news.core.domain.models.NewsList
//this function is used to convert the news list dto to news list
fun NewsListDto.toNewsList(): NewsList {
    return NewsList(
        nextPage = nextPage,
        articles = results?.map { it.toArticle() } ?: emptyList(),
    )

}

//this function is used to convert the article dto to article
fun ArticleDto.toArticle(): Article {
    return Article(
        articleId = article_id ?: "",
        title = title ?: "",
        description = description ?: "",
        pubDate = pubDate ?: "",
        sourceName = source_name ?: "",
        imageUrl = image_url ?: "",
        content = "$article_id: As of 10:30 a.m. ET Friday — more than a week after landfall — nearly 735,000 electricity accounts were still without power across five states, according to Poweroutage.us: South Carolina (273,913), North Carolina (230,448), Georgia (203,111), Florida (13,794), and Virginia (13,191).\n" +
                "\n" +
                "Helene also changed the way the water looks in the Gulf of Mexico, along Florida’s Big Bend area and much of its Gulf coastline. Three days after landfall, the effects were still dramatically visible, in waters that went from mainly clear to light blue, brown and green.\n" +
                "\n" +
                "“Helene’s winds and waves churned up sediment from the seafloor along shallow coastal areas,” according to the NASA Earth Observatory. “Light reflects from these fine particles and makes the water appear bright blue. Storm surge, flooded rivers, and flash floods produced runoff that eroded land surfaces and carried even more particles into the ocean, adding to the color.”\n" +
                "\n" +
                "The agency says the wide ribbon of brown and green colors along the coastline is likely from dissolved organic materials.\n" +
                "\n" +
                "When Helene made landfall as a Category 4 storm near Perry, Fla., late on the night of Sept. 26, the system rushed inland at speeds topping 30 mph, inflicting the perils of a barely diminished tropical storm on areas far from the coast. Making matters worse, many of those interior, elevated areas were already water-logged from rains earlier in the week.\n" +
                "\n" +
                "Satellite images from Sept. 25 and 28 — one day before landfall and more than one day afterward — show how power outages thrust parts of Florida, Georgia, South Carolina and North Carolina into darkness."
    )
}

//this function is used to convert the article entity to article to show it in the ui from the database
fun ArticleEntity.toArticle(): Article {
    return Article(
        articleId = articleId,
        title = title,
        description = description,
        pubDate = pubDate,
        sourceName = sourceName,
        imageUrl = imageUrl,
        content = content
    )
}
//this function is used to convert the article to article entity to save it in the database from the api
fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        articleId = articleId,
        title = title,
        description = description,
        pubDate = pubDate,
        sourceName = sourceName,
        imageUrl = imageUrl,
        content = content
    )
}