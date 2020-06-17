package com.yzdz.filer;

import com.yzdz.util.constant.impl.EncodingConstants;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符编码过滤器
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(EncodingConstants.UTF_8.val());
        response.setCharacterEncoding(EncodingConstants.UTF_8.val());
        // 使得继续向下传递
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
