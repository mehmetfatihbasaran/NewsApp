package com.example.newsapp.utils

enum class ArticleCategory(
    val categoryName: String
) {
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    ENTERTAINMENT("entertainment"),
    BUSINESS("business"),
    TECHNOLOGY("technology"),
    SPOTLIGHT("spotlight"),
    SPORTS("sports"),
}

fun getAllCategories(): List<ArticleCategory> {
    return listOf(
        ArticleCategory.GENERAL,
        ArticleCategory.HEALTH,
        ArticleCategory.SCIENCE,
        ArticleCategory.ENTERTAINMENT,
        ArticleCategory.BUSINESS,
        ArticleCategory.TECHNOLOGY,
        ArticleCategory.SPOTLIGHT,
        ArticleCategory.SPORTS
    )
}

fun getCategoriesByCategoryName(categoryName: String): ArticleCategory {
    return when (categoryName) {
        "general" -> ArticleCategory.GENERAL
        "health" -> ArticleCategory.HEALTH
        "science" -> ArticleCategory.SCIENCE
        "entertainment" -> ArticleCategory.ENTERTAINMENT
        "business" -> ArticleCategory.BUSINESS
        "technology" -> ArticleCategory.TECHNOLOGY
        "spotlight" -> ArticleCategory.SPOTLIGHT
        "sports" -> ArticleCategory.SPORTS
        else -> ArticleCategory.GENERAL
    }
}