package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "menu")
public class Menu {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;
    @Column
    private String router;
    @Column
    private String icon;
    @Column(name = "`order`")
    private Integer order;
    @Column
    private Integer parent;
    @Column
    private Boolean divider;
    @Transient
    private
    List<Menu> menus;

    /*public Menu(Integer _id, String _name, Boolean _active, String _router, String _icon, Integer _order, Integer _parent, Boolean _divider){
        this.id = _id;
        this.name = _name;
        this.active = _active;
        this.router = _router;
        this.icon = _icon;
        this.order = _order;
        this.parent = _parent;
        this.divider = _divider;
    }*/
}
