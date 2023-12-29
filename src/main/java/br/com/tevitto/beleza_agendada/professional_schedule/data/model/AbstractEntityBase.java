package br.com.tevitto.beleza_agendada.professional_schedule.data.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class AbstractEntityBase {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "STATUS_NAME", length = 255)
    private String statusName;

    @Column(name = "STATUS_REASON", length = 255)
    private String statusReason;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @Column(name = "CREATE_DATE", updatable = false, insertable = true)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true, insertable = false)
    @UpdateTimestamp
    private Date updateDate;
}

