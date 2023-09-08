package com.example.hikari.demo.feature.aircraft.data.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private Integer limit;
    private Integer page;
    private String sortColumn;
    private SortOrder sortOrder;


    public enum SortOrder {
        ASC("ASC"), DESC("DESC");

        private String value;

        SortOrder(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
