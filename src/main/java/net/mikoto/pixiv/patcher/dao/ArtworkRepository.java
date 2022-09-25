package net.mikoto.pixiv.patcher.dao;

import net.mikoto.pixiv.core.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author mikoto
 * @date 2022/4/17 20:24
 */
@Repository("artworkRepository")
public interface ArtworkRepository extends JpaRepository<Artwork, Integer> {
    /**
     * Get an artwork by artwork id.
     *
     * @param artworkId The artwork id.
     * @return An artwork.
     */
    Artwork getArtworkByArtworkId(Integer artworkId);

    /**
     * Get artworks by series id
     *
     * @param seriesId The series id.
     * @param pageable Page.
     * @return Artworks.
     */
    Page<Artwork> findArtworksBySeriesId(int seriesId, Pageable pageable);

    /**
     * Get artworks.
     *
     * @param tags       The tags need to find.
     * @param title      The title need to find.
     * @param authorName The author name.
     * @param pageable   Page.
     * @param grading    Grading.
     * @return Artworks.
     */
    @Query(
            value = "SELECT * from pixiv.artwork WHERE grading <= :grading and (tags like :tags or artwork_title like :title or author_name like :authorName)",
            nativeQuery = true
    )
    Page<Artwork> findArtworks(@Param("grading") int grading, @Param("tags") String tags, @Param("title") String title, @Param("authorName") String authorName, Pageable pageable);
}
