package com.back.domain.post.post.repository;

import com.back.domain.member.member.entity.Member;
import com.back.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByOrderByIdDesc();

    Optional<Post> findFirstByOrderByIdDesc();

    Optional<Post> findTop1ByAuthorAndPublishedAndTitleOrderByIdDesc(Member author, boolean published, String title);

    long countByPublished(boolean published);

    long countByListed(boolean listed);
}