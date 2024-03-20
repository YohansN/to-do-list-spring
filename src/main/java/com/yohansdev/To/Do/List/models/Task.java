package models;

import jakarta.persistence.*;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "tasks")
@Entity(name = "task")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date deadline;
    //private boolean isComplete; adicionar posteriormente caso pedido pelo cliente!

    //Método para formatar a data de entrega com dia/mês/ano hora:minuto
    public void setDeadline(String deadlineString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.deadline = dateFormat.parse(deadlineString);
    }
}

