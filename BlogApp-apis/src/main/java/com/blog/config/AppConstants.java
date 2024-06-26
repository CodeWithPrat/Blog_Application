// This package contains configuration-related classes for the blog application
package com.blog.config;

// This class defines constant values used throughout the application
public class AppConstants {

    // Default page number for pagination, starting from 0
    public static final String PAGE_NUMBER = "0";

    // Default number of items per page
    public static final String PAGE_SIZE = "10";

    // Default field to sort by (in this case, posts will be sorted by their ID)
    public static final String SORT_BY = "postId";

    // Default sort direction (ascending order)
    public static final String SORT_DIR = "asc";

    // ID number for a normal user role
    public static final Integer NORMAL_USER = 502;

    // ID number for an admin user role
    public static final Integer ADMIN_USER = 501;
}