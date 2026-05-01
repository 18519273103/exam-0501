SELECT 
    c.course_id, 
    c.course_name, 
    COUNT(e.student_id) AS enroll_count
FROM courses c
JOIN enrollments e ON c.course_id = e.course_id
WHERE 
    c.course_type = '专业课'
GROUP BY 
    c.course_id, 
    c.course_name
HAVING 
    COUNT(e.student_id) > 50
ORDER BY 
    enroll_count ASC;