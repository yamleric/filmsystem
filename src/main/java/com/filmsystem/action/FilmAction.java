package com.filmsystem.action;

import com.filmsystem.entity.Film;
import com.filmsystem.entity.FilmCategory;
import com.filmsystem.service.CategoryService;
import com.filmsystem.service.FilmService;
import com.filmsystem.util.DateUtil;
import com.filmsystem.util.FileStorageUtil;
import com.filmsystem.util.PageResult;
import com.filmsystem.util.StringUtil;
import com.filmsystem.util.UploadPathUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller("filmAction")
@Scope("prototype")
public class FilmAction extends ActionSupport {
    private FilmService filmService;
    private CategoryService categoryService;
    private Long id;
    private Long categoryId;
    private Film film = new Film();
    private List<Film> filmList;
    private List<FilmCategory> categoryList;
    private PageResult<Film> pageResult;
    private String keyword;
    private int page = 1;
    private int pageSize = 10;
    private String releaseDate;
    private String errorMessage;
    private File poster;
    private String posterFileName;
    private File upload;
    private String uploadFileName;

    @Autowired
    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String list() {
        prepareForm();
        pageResult = filmService.search(keyword, categoryId, page, pageSize);
        filmList = pageResult.getRecords();
        return SUCCESS;
    }

    public String add() {
        prepareForm();
        film = new Film();
        return SUCCESS;
    }

    public String edit() {
        prepareForm();
        film = filmService.get(id);
        if (film == null) {
            return INPUT;
        }
        if (film.getCategory() != null) {
            categoryId = film.getCategory().getId();
        }
        releaseDate = DateUtil.formatDate(film.getReleaseDate());
        return SUCCESS;
    }

    public String save() {
        try {
            fillFilm();
            saveFiles();
            filmService.save(film);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            prepareForm();
            return INPUT;
        } catch (IOException e) {
            errorMessage = "文件上传失败: " + e.getMessage();
            prepareForm();
            return INPUT;
        }
    }

    public String update() {
        try {
            fillFilm();
            saveFiles();
            filmService.update(film);
            return SUCCESS;
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
            prepareForm();
            return INPUT;
        } catch (IOException e) {
            errorMessage = "文件上传失败: " + e.getMessage();
            prepareForm();
            return INPUT;
        }
    }

    public String delete() {
        filmService.delete(id);
        return SUCCESS;
    }

    public String view() {
        film = filmService.get(id);
        return film == null ? INPUT : SUCCESS;
    }

    private void prepareForm() {
        categoryList = categoryService.list();
    }

    private void fillFilm() {
        FilmCategory category = categoryService.get(categoryId);
        film.setCategory(category);
        film.setReleaseDate(DateUtil.parseDate(releaseDate));
    }

    private void saveFiles() throws IOException {
        ServletContext context = ServletActionContext.getServletContext();
        File uploadRoot = UploadPathUtil.resolveRoot(context);
        String posterFolder = new File(uploadRoot, "posters").getPath();
        String filmFolder = new File(uploadRoot, "films").getPath();
        String savedPoster = FileStorageUtil.save(poster, posterFileName, posterFolder);
        String savedFilm = FileStorageUtil.save(upload, uploadFileName, filmFolder);
        if (!StringUtil.isBlank(savedPoster)) {
            film.setPosterPath("/uploads/posters/" + savedPoster);
        }
        if (!StringUtil.isBlank(savedFilm)) {
            film.setVideoPath("/uploads/films/" + savedFilm);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public List<FilmCategory> getCategoryList() {
        return categoryList;
    }

    public PageResult<Film> getPageResult() {
        return pageResult;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public File getPoster() {
        return poster;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
}
