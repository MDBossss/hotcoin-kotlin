import express, { Router } from "express";
import ArticleController from "../controllers/ArticleController";

const router: Router = express.Router();
const articleController = new ArticleController();

router.use(express.json());

router.get("/latest", articleController.getLastPublishedArticle);
router.get("/:userId", articleController.getAllArticles);
router.post("/:userId", articleController.createArticle);
router.delete("/:userId/:title", articleController.deleteArticle);

export default router;
