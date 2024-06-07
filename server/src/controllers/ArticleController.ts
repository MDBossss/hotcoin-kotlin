import { Request, Response } from "express";
import { prisma } from "../utils/prisma";
import { Article, Source } from "@prisma/client";

interface ArticleWithSource extends Article {
  source: Source;
}

class ArticleController {
  async getAllArticles(req: Request, res: Response) {
    try {
      console.log("[INFO] getAllArticles()");
      const articles = await prisma.article.findMany({
        include: { source: true },
      });
      return res.status(200).json(articles);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async getLastPublishedArticle(req: Request, res: Response) {
    try {
      console.log("[INFO] getLastPublishedArticle()");
      const articles = await prisma.article.findMany({
        orderBy: {
          publishedAt: "desc",
        },
        take: 1,
      });

      if (articles.length < 1) {
        return res.status(404).json({ error: "No articles found" });
      }

      res.status(200).json(articles[0]);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal server error" });
    }
  }

  async createArticle(req: Request, res: Response) {
    try {
      console.log("[INFO] createArticle()");

      const article: ArticleWithSource = req.body;

      const existingArticle = await prisma.article.findFirst({
        where: {
          title: article.title,
        },
      });

      if (existingArticle) {
        return res.status(200).json({ message: "Article already bookmarked" });
      }

      const newArticle = await prisma.article.create({
        data: {
          source: { create: { name: article.source.name } },
          author: article.author,
          title: article.title,
          description: article.description,
          url: article.url,
          urlToImage: article.urlToImage,
          publishedAt: article.publishedAt,
          content: article.content,
        },
      });

      res.status(201).json(newArticle);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async deleteArticle(req: Request, res: Response) {
    try {
      console.log("[INFO] deleteArticle()");
      const { title } = req.params;

      await prisma.article.delete({ where: { title } });
      res.status(204).json({ message: "Article deleted successfully" });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
}

export default ArticleController;
