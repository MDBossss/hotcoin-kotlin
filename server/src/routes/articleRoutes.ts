import express, { Router } from "express";
import ArticleController from "../controllers/ArticleController";

const router: Router = express.Router();
const articleController = new ArticleController();

router.use(express.json());

router.get("/", articleController.getAllArticles);
router.get("/latest", articleController.getLastPublishedArticle);
router.post("/", articleController.createArticle);
router.delete("/:title", articleController.deleteArticle);

export default router;
