package com.smart.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.smart.entities.Player;
import com.smart.entities.Sport;

import java.util.List;

public class PlayerDAO {
    private Session session;

    public PlayerDAO(Session session) {
        this.session = session;
    }

    /**
     * Retrieves players based on gender, level, and age.
     *
     * @param gender The gender of the players.
     * @param level  The level of the players.
     * @param age    The age of the players.
     * @return A list of players matching the given criteria.
     */
    public List<Player> getPlayersByGenderLevelAndAge(String gender, int level, int age) {
        String hql = "FROM Player WHERE gender = :gender AND level = :level AND age = :age";
        Query<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("gender", gender);
        query.setParameter("level", level);
        query.setParameter("age", age);
        return query.getResultList();
    }

    /**
     * Retrieves sports associated with multiple players.
     *
     * @return A list of sports associated with multiple players.
     */
    public List<Sport> getSportsAssociatedWithMultiplePlayers() {
        String hql = "SELECT s FROM Sport s JOIN s.players p GROUP BY s HAVING COUNT(p) >= 2";
        Query<Sport> query = session.createQuery(hql, Sport.class);
        return query.getResultList();
    }

    /**
     * Retrieves sports with no players associated.
     *
     * @return A list of sports with no players associated.
     */
    public List<Sport> getSportsWithNoPlayers() {
        String hql = "SELECT s FROM Sport s WHERE s.players IS EMPTY";
        Query<Sport> query = session.createQuery(hql, Sport.class);
        return query.getResultList();
    }

    /**
     * Updates the player's information in the database.
     *
     * @param player The player object to be updated.
     */
    public void updatePlayer(Player player) {
        session.saveOrUpdate(player);
    }

    /**
     * Deletes a sport and its associated data from the database.
     *
     * @param sport The sport object to be deleted.
     */
    public void deleteSport(Sport sport) {
        session.delete(sport);
    }
}


