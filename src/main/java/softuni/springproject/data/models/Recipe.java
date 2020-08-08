package softuni.springproject.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.springproject.data.models.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {

    @Column
    private String name;

    @Column
    private int difficulty;

    @Column
    private int taste;

    @ManyToMany(mappedBy = "recipes")
    List<Chef> chefs;

}
