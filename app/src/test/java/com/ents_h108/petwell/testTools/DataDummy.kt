package com.ents_h108.petwell.testTools

import com.ents_h108.petwell.data.model.Article

object DataDummy {
    fun generateDummyDataArticle(): List<Article> {
        val articleList = ArrayList<Article>()
        for (i in 0 until 10) {
            val article = Article(
                "_id_$i",
                "Title $i",
                "Description of article $i",
                "https://lh3.googleusercontent.com/d/1zfpAAt0H61JrxuuuIAhpkFvnxJyRxPqm",
                "artikel",
                "2022-02-22T22:22:22Z"
            )
            articleList.add(article)
        }
        return articleList
    }
}