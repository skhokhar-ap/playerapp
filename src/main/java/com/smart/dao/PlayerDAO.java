package com.smart.dao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.smart.entities.Player;
import com.smart.entities.Sport;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smart.entities.Player;
import com.smart.entities.Sport;

import java.util.List;

@Repository
public class PlayerDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void savePlayer(Player player) {
        getCurrentSession().save(player);
    }

    public Player getPlayerById(Long id) {
        return getCurrentSession().get(Player.class, id);
    }

    public void updatePlayer(Player player) {
        getCurrentSession().merge(player);
    }

    public void deletePlayer(Player player) {
        getCurrentSession().delete(player);
    }

    public Sport getSportById(Long id) {
        return getCurrentSession().get(Sport.class, id);
    }

    public List<Player> getAllPlayers() {
        String hql = "FROM Player";
        Query<Player> query = getCurrentSession().createQuery(hql, Player.class);
        return query.getResultList();
    }

    public List<Player> getPlayersByGenderLevelAndAge(String gender, int level, int age) {
        String hql = "FROM Player WHERE gender = :gender AND level = :level AND age = :age";
        Query<Player> query = getCurrentSession().createQuery(hql, Player.class);
        query.setParameter("gender", gender);
        query.setParameter("level", level);
        query.setParameter("age", age);
        return query.getResultList();
    }

    public List<Sport> getSportsWithPlayers(List<String> names) {
        String hql = "SELECT s FROM Sport s JOIN FETCH s.players p WHERE s.name IN :names";
        Query<Sport> query = getCurrentSession().createQuery(hql, Sport.class);
        query.setParameter("names", names);
        return query.getResultList();
    }

    public List<Player> getPlayersWithNoSports() {
        String hql = "SELECT p FROM Player p WHERE p.sports IS EMPTY";
        Query<Player> query = getCurrentSession().createQuery(hql, Player.class);
        return query.getResultList();
    }

    public void deleteSport(Sport sport) {
        getCurrentSession().delete(sport);
    }

    public List<Player> getPaginatedPlayerList(int page, int size, String sportCategory) {
        String hql = "SELECT p FROM Player p JOIN FETCH p.sports s";
        if (sportCategory != null) {
            hql += " WHERE s.name = :sportCategory";
        }
        Query<Player> query = getCurrentSession().createQuery(hql, Player.class);
        if (sportCategory != null) {
            query.setParameter("sportCategory", sportCategory);
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
