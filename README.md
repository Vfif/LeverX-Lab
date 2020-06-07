# Blog
### REST API:  
•	Registration  
•	Authorization  
•	View articles  
•	Add new article  
•	Edit articles  
•	Remove article  
•	Change article state: public/draft  
•	Add tag  
•	Search by tag and article name  
•	Add comments

User {  
    id: Integer/UID,  
    first_name: String,  
    last_name: String,  
    password: String,  
    email: String,  
    created_at: Date  
}

Article {  
    id: Integer/UID,  
    title: String,  
    text: Text,  
    status: Enum,  
    author_id: Integer/UID  
    created_at: Date,  
    updated_at: Date  
}

Comment {  
    id: Integer/UID,  
    message: Text,  
    post_id: Integer/UID,  
    author_id: Integer/UID  
    created_at: Date  
}

Tag {  
    id: Integer/UID,  
    name: String  
}

### Recommendations  
•	JWT ( JSON Web Token )  
•	Postman  
•	Docker
