package min.onlineshop.dtos;

import min.onlineshop.domains.User;
import min.onlineshop.requests.PageInfo;
import min.onlineshop.services.impl.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromString;

public class MyCommon {

    public static PageRequest getPageable(PageInfo pageInfo) {
        String order = pageInfo.getOrderBy() == null ? "createdDate" : pageInfo.getOrderBy();
        Sort.Direction sortDirection = pageInfo.getDirection() == null ? DESC : fromString(pageInfo.getDirection());

        return PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sortDirection, order);
    }

    public static PageRequest getPageable(PageInfo pageInfo, String defaultOrder) {
        String order = pageInfo.getOrderBy() == null ? defaultOrder : pageInfo.getOrderBy();
        Sort.Direction sortDirection = pageInfo.getDirection() == null ? DESC : fromString(pageInfo.getDirection());

        return PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sortDirection, order);
    }

    public static boolean isNotNull(String str){
        if(str != null && !"".equals(str))
            return true;
        return false;
    }

    public static User getCurrentUser(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
