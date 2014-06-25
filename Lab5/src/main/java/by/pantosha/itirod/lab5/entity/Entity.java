package by.pantosha.itirod.lab5.entity;

public class Entity implements IEntity<Integer> {
    private Integer id;

    public Integer getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
