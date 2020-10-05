package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.List;
@Data
@NoArgsConstructor
public class CategoryDto {
    private String title;
}
