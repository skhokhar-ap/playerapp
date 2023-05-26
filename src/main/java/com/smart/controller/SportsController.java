package com.smart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smart.dao.PlayerDAO;
import com.smart.entities.Player;
import com.smart.entities.Sport;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smart.dao.PlayerDAO;
import com.smart.entities.Player;
import com.smart.entities.Sport;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SportsController {
    private final PlayerDAO playerDAO;

    @Autowired
    public SportsController(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @GetMapping("/sports")
    public ResponseEntity<List<Sport>> getSportsWithPlayers(@RequestParam List<String> names) {
        List<Sport> sports = playerDAO.getSportsWithPlayers(names);
        if (sports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sports);
    }

    @GetMapping("/players/no-sports")
    public ResponseEntity<List<Player>> getPlayersWithNoSports() {
        List<Player> players = playerDAO.getPlayersWithNoSports();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }

    @PutMapping("/players/{playerId}/sports")
    public ResponseEntity<String> updatePlayerSports(
            @PathVariable Long playerId,
            @RequestBody List<Sport> sports
    ) {
        Player player = playerDAO.getPlayerById(playerId);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        player.setSports(sports);
        playerDAO.updatePlayer(player);
        return ResponseEntity.ok("Player sports updated successfully");
    }

    @DeleteMapping("/sports/{sportId}")
    public ResponseEntity<String> deleteSportAndAssociatedData(@PathVariable Long sportId) {
        Sport sport = playerDAO.getSportById(sportId);
        if (sport == null) {
            return ResponseEntity.notFound().build();
        }
        playerDAO.deleteSport(sport);
        return ResponseEntity.ok("Sport and associated data deleted successfully");
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPaginatedPlayerList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String sportCategory
    ) {
        List<Player> players = playerDAO.getPaginatedPlayerList(page, size, sportCategory);
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }
}
