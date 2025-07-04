package min.gob.ec.tracingservices.model.common;

import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

@Entity(name = "rolemenu")
public class RoleMenu {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)

    private Menu menu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
