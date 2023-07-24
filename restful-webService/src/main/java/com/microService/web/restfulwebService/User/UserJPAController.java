package com.microService.web.restfulwebService.User;

import com.microService.web.restfulwebService.Repository.PostRepository;
import com.microService.web.restfulwebService.Repository.UserRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJPAController(UserRepository repository, PostRepository postRepository) {
        this.userRepository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{Id}")
    public Optional<User> getUserById(@PathVariable Integer Id){
        Optional<User> user = userRepository.findById(Id);

        if (user == null){
            throw new UserNotFoundExeption(Id);
        }

        return user;
    }

    //HATEOAS example
    //URL http://localhost:8080/users
    //HATEOAS: wrap data to a hyperlink and beacome a part of return object
    //EntitiModel
    //WebMvcLinkBuilder

    @GetMapping("/jpa/userHATEOAS/{Id}")
    public EntityModel<Optional<User>> getUserByIdHATEOAS(@PathVariable Integer Id){
        Optional<User> user = userRepository.findById(Id);

        if (user == null){
            throw new UserNotFoundExeption(Id);
        }

        EntityModel<Optional<User>> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());

        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }



    @PostMapping("/jpa/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user){
        User saveUserc = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveUserc.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{Id}")
    public void deleteUserById(@PathVariable Integer Id){
        userRepository.deleteById(Id);
    }


    @GetMapping("/jpa/users/{Id}/posts")
    public List<Post> GetPostByUserId(@PathVariable Integer Id){
        Optional<User> user = userRepository.findById(Id);

        if (user == null){
            throw new UserNotFoundExeption(Id);
        }

        return user.get().getPosts();

    }

    @PostMapping("/jpa/users/{Id}/posts")
    public ResponseEntity<Object> CreatePostForUser(@PathVariable Integer Id, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(Id);

        if (user == null){
            throw new UserNotFoundExeption(Id);
        }
        post.setUser(user.get());

        Post savePOst =  postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savePOst.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

}
