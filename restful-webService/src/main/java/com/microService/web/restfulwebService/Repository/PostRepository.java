package com.microService.web.restfulwebService.Repository;

import com.microService.web.restfulwebService.User.Post;
import com.microService.web.restfulwebService.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
