package org.andrea.database;



import org.andrea.auth.User;
import org.andrea.auth.UserManager;
import org.andrea.collection.MusicBandDequeManager;
import org.andrea.data.*;
import org.andrea.exceptions.CollectionException;
import org.andrea.exceptions.DatabaseException;
import org.andrea.exceptions.InvalidDataException;
import org.andrea.exceptions.InvalidEnumException;
import org.andrea.log.Log;
import org.andrea.utils.DateConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MusicBandDatabaseManager extends MusicBandDequeManager {
    //language=SQL
    private final static String INSERT_MUSIC_BAND_QUERY = "INSERT INTO BANDS (name, coordinates_x, coordinates_y, creation_date, followers, birth_band_date, genre, status, album_full_name, album_type, user_login,id)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,DEFAULT) RETURNING id; ";
    private final DatabaseHandler databaseHandler;
    private final UserManager userManager;

    public MusicBandDatabaseManager(DatabaseHandler c, UserManager userManager) throws DatabaseException {
        super();
        databaseHandler = c;
        this.userManager = userManager;
        create();
    }

    private void create() throws DatabaseException {
//        language=SQL
        String create =
                "CREATE TABLE IF NOT EXISTS BANDS (" +
                        "id SERIAL PRIMARY KEY CHECK ( id > 0 )," +
                        "name TEXT NOT NULL CHECK (name <> '')," +
                        "coordinates_x FLOAT NOT NULL ," +
                        "coordinates_y BIGINT NOT NULL CHECK (coordinates_y > -123 )," +
                        "creation_date TEXT NOT NULL," +
                        "followers BIGINT NOT NULL CHECK(followers > 0)," +
                        "birth_band_date TEXT," +
                        "genre TEXT," +
                        "status TEXT NOT NULL," +
                        "album_full_name VARCHAR(1237) CHECK (album_full_name <> '')," +
                        "album_type TEXT NOT NULL," +
                        "user_login TEXT NOT NULL REFERENCES USERS(login)" +
                        ");";

        try (PreparedStatement createStatement = databaseHandler.getPreparedStatement(create)) {
            createStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot create bands database");
        }
    }

    @Override
    public int generateNextId() {
        try (PreparedStatement statement = databaseHandler.getPreparedStatement("SELECT nextval('id')")) {
            ResultSet r = statement.executeQuery();
            r.next();
            return r.getInt(1);
        } catch (SQLException e) {
            return 1;
        }
    }

    private void setBand(PreparedStatement statement, MusicBand musicBand) throws SQLException {
        statement.setString(1, musicBand.getName());
        statement.setFloat(2, musicBand.getCoordinates().getX());
        statement.setLong(3, musicBand.getCoordinates().getY());
        statement.setString(4, DateConverter.dateToString(musicBand.getCreationDate()));
        statement.setLong(5, musicBand.getFollowers());


        if (musicBand.getBirthBandDate() == null) statement.setString(6, null);
        else statement.setString(6, DateConverter.dateToString(musicBand.getBirthBandDate()));

        if (musicBand.getGenre() == null) statement.setString(7, null);
        else statement.setString(7, musicBand.getGenre().toString());


        statement.setString(8, musicBand.getStatus().toString());

        statement.setString(9, musicBand.getBestAlbum().getFullName());
        statement.setString(10, musicBand.getBestAlbum().getType().toString());

        statement.setString(11, musicBand.getUserLogin());

    }

    private MusicBand getBand(ResultSet resultSet) throws SQLException, InvalidDataException {
        Coordinates coordinates = new Coordinates(resultSet.getFloat("coordinates_x"), resultSet.getLong("coordinates_y"));
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        Date creationDate = DateConverter.parseDate(resultSet.getString("creation_date"));
        long followers = resultSet.getLong("followers");

        String endDateStr = resultSet.getString("birth_band_date");
        LocalDate endDate = null;
        if (endDateStr != null) endDate = DateConverter.parseLocalDate(endDateStr);

        String genreStr = resultSet.getString("genre");
        Genre genre = null;
        if (genreStr != null) {
            try {
                genre = Genre.valueOf(genreStr);
            } catch (IllegalArgumentException e) {
                throw new InvalidEnumException();
            }
        }
        Status status;
        AlbumType type;
        try {
            status = Status.valueOf(resultSet.getString("status"));
            type = AlbumType.valueOf(resultSet.getString("album_type"));
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException();
        }
        String fullName = resultSet.getString("album_full_name");
        BestAlbum bestAlbum = new BestAlbum(fullName, type);
        MusicBand musicBand = new MusicBand(name, coordinates, followers, endDate, genre, status, bestAlbum);
        musicBand.setCreationDate(creationDate);
        musicBand.setId(id);
        musicBand.setUserLogin(resultSet.getString("user_login"));
        if (!userManager.isPresent(musicBand.getUserLogin())) throw new DatabaseException("no user found");
        return musicBand;
    }

    @Override
    public void add(MusicBand musicBand) {

        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(INSERT_MUSIC_BAND_QUERY, true)) {
            setBand(statement, musicBand);
            if (statement.executeUpdate() == 0) throw new DatabaseException();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (!resultSet.next()) throw new DatabaseException();
            musicBand.setId(resultSet.getInt(resultSet.findColumn("id")));

            databaseHandler.commit();
        } catch (SQLException | DatabaseException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add to database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(musicBand);
    }

    @Override
    public void removeByID(Integer id) {
        //language=SQL
        String query = "DELETE FROM BANDS WHERE id = ?;";
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot remove from database");
        }
        super.removeByID(id);
    }

    @Override
    public void removeFirst() {
        removeByID(getCollection().getFirst().getId());
    }

    @Override
    public void updateByID(Integer id, MusicBand musicBand) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        //language=SQL
        String sql = "UPDATE BANDS SET " +
                "name=?," +
                "coordinates_x=?," +
                "coordinates_y=?," +
                "creation_date=?," +
                "followers=?," +
                "birth_band_date=?," +
                "genre=?," +
                "status=?," +
                "album_full_name=?," +
                "album_type=?," +
                "user_login=?" +
                "WHERE id=?";
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(sql)) {
            setBand(statement, musicBand);
            statement.setInt(12, id);
            statement.execute();
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot update musicBand #" + musicBand.getId() + " in database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.updateByID(id, musicBand);
    }

    @Override
    public void addIfMax(MusicBand musicBand) {
        //language=SQL
        String getMaxQuery = "SELECT MAX(followers) FROM BANDS";

        if (getCollection().isEmpty()) {
            add(musicBand);
            return;
        }
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (Statement getStatement = databaseHandler.getStatement();
             PreparedStatement insertStatement = databaseHandler.getPreparedStatement(INSERT_MUSIC_BAND_QUERY)) {

            ResultSet resultSet = getStatement.executeQuery(getMaxQuery);
            if (!resultSet.next()) throw new DatabaseException("unable to add");

            long maxFollowers = resultSet.getLong(1);
            if (musicBand.getFollowers() < maxFollowers)
                throw new DatabaseException("unable to add, max followers is " + maxFollowers + " current followers is " + musicBand.getFollowers());

            setBand(insertStatement, musicBand);

            musicBand.setId(resultSet.getInt("id"));
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add due to internal error");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(musicBand);
    }

    @Override
    public void addIfMin(MusicBand musicBand) {
        //language=SQL
        String getMaxQuery = "SELECT MIN(followers) FROM BANDS";

        if (getCollection().isEmpty()) {
            add(musicBand);
            return;
        }
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (Statement getStatement = databaseHandler.getStatement();
             PreparedStatement insertStatement = databaseHandler.getPreparedStatement(INSERT_MUSIC_BAND_QUERY)) {

            ResultSet resultSet = getStatement.executeQuery(getMaxQuery);
            if (!resultSet.next()) throw new DatabaseException("unable to add");

            long minFollowers = resultSet.getLong(1);
            if (musicBand.getFollowers() > minFollowers)
                throw new DatabaseException("unable to add, min followers is " + minFollowers + " current followers is " + musicBand.getFollowers());

            setBand(insertStatement, musicBand);

            musicBand.setId(resultSet.getInt("id"));
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add due to internal error");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(musicBand);
    }

    public void clear(User user) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement statement = databaseHandler.getPreparedStatement("DELETE FROM BANDS WHERE user_login=? RETURNING id")) {
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                ids.add(id);
            }
        } catch (SQLException | CollectionException e) {
            databaseHandler.rollback();
            deserializeCollection("");
            throw new DatabaseException("cannot clear database");
        } finally {
            databaseHandler.setNormalMode();
        }
        removeAll(ids);
    }

    @Override
    public void deserializeCollection(String ignored) {
        if (!getCollection().isEmpty()) super.clear();
        //language=SQL
        String query = "SELECT * FROM BANDS";
        try (PreparedStatement selectAllStatement = databaseHandler.getPreparedStatement(query)) {
            ResultSet resultSet = selectAllStatement.executeQuery();
            int damagedElements = 0;
            while (resultSet.next()) {
                try {
                    MusicBand musicBand = getBand(resultSet);
                    if (!musicBand.validate()) throw new InvalidDataException("element is damaged");
                    super.addWithoutIdGeneration(musicBand);
                } catch (InvalidDataException | SQLException e) {
                    damagedElements += 1;
                }
            }
            if (super.getCollection().isEmpty()) throw new DatabaseException("nothing to load");
            if (damagedElements == 0) Log.logger.info("collection successfully loaded");
            else Log.logger.warn(damagedElements + " elements are damaged");
        } catch (SQLException e) {
            throw new DatabaseException("cannot load");
        }

    }
}
