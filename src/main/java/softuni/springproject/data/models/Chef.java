package softuni.springproject.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.springproject.data.models.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chefs")
public class Chef extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private int skill;

    @Column
    private int reputation;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "chef_recipes",
            joinColumns = {@JoinColumn(name = "chef_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")}
    )
    private List<Recipe> recipes;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
