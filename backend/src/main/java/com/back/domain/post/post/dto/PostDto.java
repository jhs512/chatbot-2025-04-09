package com.back.domain.post.post.dto;

import com.back.domain.post.post.entity.Post;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    @NonNull
    private final long id;

    @NonNull
    private final LocalDateTime createDate;

    @NonNull
    private final LocalDateTime modifyDate;

    @NonNull
    private final long authorId;

    @NonNull
    private final String authorName;

    @NonNull
    private final String authorProfileImgUrl;

    @NonNull
    private final String title;

    @NonNull
    private final boolean published;

    @NonNull
    private final boolean listed;

    @NonNull
    private final String thumbnailImgUrl;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.authorId = post.getAuthor().getId();
        this.authorName = post.getAuthor().getName();
        this.authorProfileImgUrl = post.getAuthor().getProfileImgUrlOrDefault();
        this.title = post.getTitle();
        this.published = post.isPublished();
        this.listed = post.isListed();
        this.thumbnailImgUrl = post.getThumbnailImgUrlOrDefault();
    }
}
