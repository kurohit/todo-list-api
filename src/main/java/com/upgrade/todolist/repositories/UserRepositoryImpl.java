package com.upgrade.todolist.repositories;

import com.upgrade.todolist.domain.User;
import com.upgrade.todolist.exceptions.TLBadRequestException;
import com.upgrade.todolist.exceptions.TodoAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT = "INSERT INTO users(user_id, name, email, password) VALUES(NEXTVAL('users_seq'), ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String SQL_FIND_BY_ID = "SELECT user_id, name, email, password " + "FROM users WHERE user_id = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT user_id, name, email, password " + "FROM users WHERE email = ?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE email = ?";


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer addUser(String name, String email, String password) throws TodoAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps  = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("user_id");
        }catch (Exception e) {
            throw new TodoAuthException("Invalid details. Failed to create account!!!");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws TodoAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new TodoAuthException("Invalid email/password!!!");
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new TodoAuthException("Invalid email/password!!!");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findByID(Integer userID) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID}, userRowMapper);
    }

    @Override
    public void changePassword(String email, String newPassword) {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
        try{
            jdbcTemplate.update(SQL_CHANGE_PASSWORD, new Object[]{hashedPassword, email});
        }catch (Exception e) {
            throw new TLBadRequestException("Incorrect email/password!!!");
        }
    }

    @Override
    public void deleteUserByID(Integer userID) {

    }


    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"));

    });
}
