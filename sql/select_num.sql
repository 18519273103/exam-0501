SELECT 
    c.course_id, 
    c.course_name, 
    COUNT(e.student_id) AS enroll_count
FROM courses c
LEFT JOIN enrollments e ON c.course_id = e.course_id
GROUP BY 
    c.course_id, 
    c.course_name
ORDER BY 
    enroll_count DESC;