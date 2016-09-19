package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import shared.definitions.PlayerIndex;
import shared.exceptions.JoinGameException;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * The top level client model.
 * Represents a full game.
 */
@Generated("net.kupiakos")
public class ClientModel {

    @SerializedName("chat")
    @Expose
    private MessageList chat;

    @SerializedName("winner")
    @Expose
    private PlayerIndex winner;

    @SerializedName("turnTracker")
    @Expose
    private TurnTracker turnTracker;

    @SerializedName("map")
    @Expose
    private Map map;

    @SerializedName("bank")
    @Expose
    private ResourceList bank;

    @SerializedName("tradeOffer")
    @Expose
    private TradeOffer tradeOffer;

    @SerializedName("players")
    @Expose
    private List<Player> players = new ArrayList<Player>();

    @SerializedName("version")
    @Expose
    private int version;

    @SerializedName("log")
    @Expose
    private MessageList log;


    /**
     * No args constructor for use in serialization
     */
    public ClientModel() {
    }

    /**
     * @param chat        all the chat messages the players have sent to each other
     * @param winner      the player who has won the game, or null if nobody
     * @param turnTracker tracks whose turn it is and what action's being done
     * @param map         the current {@link Map} of the game
     * @param bank        the set of cards available to be distributed to the players
     * @param tradeOffer  the current trade offer, if there is one
     * @param players     the current {@link Player}s in the game, from 2-4
     * @param version     the version of the model - incremented whenever anyone makes a move
     * @param log         all the log messages for the game's progress
     */
    public ClientModel(@NotNull MessageList chat,
                       @Nullable PlayerIndex winner,
                       @NotNull TurnTracker turnTracker,
                       @NotNull Map map,
                       @NotNull ResourceList bank,
                       @NotNull TradeOffer tradeOffer,
                       @NotNull List<Player> players,
                       int version,
                       @NotNull MessageList log) {
        this.chat = chat;
        this.winner = winner;
        this.turnTracker = turnTracker;
        this.map = map;
        this.bank = bank;
        this.tradeOffer = tradeOffer;
        this.players = players;
        this.version = version;
        this.log = log;
    }

    // CUSTOM CODE
    @NotNull
    public int getPlayerCount() {
        return this.getPlayers().size();
    }


    // END CUSTOM CODE

    public boolean canPlayerJoin(@NotNull Player player) {
        return false;
    }

    public void playerJoin(@NotNull Player player) {
        if (!canPlayerJoin(player)) {
            throw new JoinGameException("Could not join the game");
        }
    }

    /**
     * @return all the chat messages the players have sent to each other
     */
    @NotNull
    public MessageList getChat() {
        return chat;
    }

    /**
     * @param chat all the chat messages the players have sent to each other
     */
    public void setChat(@NotNull MessageList chat) {
        this.chat = chat;
    }

    public ClientModel withChat(@NotNull MessageList chat) {
        setChat(chat);
        return this;
    }

    /**
     * @return the player who has won the game, or null if nobody
     */
    @Nullable
    public PlayerIndex getWinner() {
        return winner;
    }

    /**
     * Sets the player who has won the game, or null if nobody
     *
     * @param winner the player who has won the game, or null if nobody
     */
    public void setWinner(@Nullable PlayerIndex winner) {
        this.winner = winner;
    }

    public ClientModel withWinner(@Nullable PlayerIndex winner) {
        setWinner(winner);
        return this;
    }

    /**
     * Tracks whose turn it is and what action's being done
     *
     * @return the object that tracks whose turn it is and what action's being done
     */
    @NotNull
    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    /**
     * @param turnTracker tracks whose turn it is and what action's being done
     */
    public void setTurnTracker(@NotNull TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public ClientModel withTurnTracker(@NotNull TurnTracker turnTracker) {
        setTurnTracker(turnTracker);
        return this;
    }

    /**
     * Gets the current {@link Map} of the game.
     *
     * @return the current {@link Map} of the game
     */
    @NotNull
    public Map getMap() {
        return map;
    }

    /**
     * Sets the {@link Map} of the game.
     *
     * @param map the current {@link Map} of the game
     */
    public void setMap(@NotNull Map map) {
        this.map = map;
    }

    /**
     * Sets the {@link Map} of the game.
     *
     * @param map the current {@link Map} of the game
     * @return the current {@link Map} of the game
     */
    public ClientModel withMap(@NotNull Map map) {
        setMap(map);
        return this;
    }

    /**
     * @return the set of cards available to be distributed to the players
     */
    @NotNull
    public ResourceList getBank() {
        return bank;
    }

    /**
     * @param bank the set of cards available to be distributed to the players
     */
    public void setBank(@NotNull ResourceList bank) {
        this.bank = bank;
    }

    public ClientModel withBank(@NotNull ResourceList bank) {
        setBank(bank);
        return this;
    }

    /**
     * @return The current trade offer, if there is one.
     */
    @NotNull
    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    /**
     * @param tradeOffer the current trade offer, if there is one
     */
    public void setTradeOffer(@NotNull TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    public ClientModel withTradeOffer(@NotNull TradeOffer tradeOffer) {
        setTradeOffer(tradeOffer);
        return this;
    }

    /**
     * Gets the {@link Player}s of the game, from 2-4.
     *
     * @return The {@link Player}s currently playing this game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the {@link Player}s of the game, from 2-4.
     *
     * @param players The {@link Player}s currently playing this game
     */
    public void setPlayers(@NotNull List<Player> players) {
        this.players = players;
    }

    public ClientModel withPlayers(@NotNull List<Player> players) {
        setPlayers(players);
        return this;
    }

    /**
     * @return The version of the model. This is incremented whenever anyone makes a move.
     */
    @NotNull
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version of the model - incremented whenever anyone makes a move
     */
    public void setVersion(int version) {
        this.version = version;
    }

    public ClientModel withVersion(int version) {
        setVersion(version);
        return this;
    }

    /**
     * @return All the log messages.
     */
    @NotNull
    public MessageList getLog() {
        return log;
    }

    /**
     * @param log All the log messages.
     */
    public void setLog(@NotNull MessageList log) {
        this.log = log;
    }

    public ClientModel withLog(@NotNull MessageList log) {
        setLog(log);
        return this;
    }

    @Override
    public String toString() {
        return "ClientModel [" +
                "chat=" + chat +
                ", winner=" + winner +
                ", turnTracker=" + turnTracker +
                ", map=" + map +
                ", bank=" + bank +
                ", tradeOffer=" + tradeOffer +
                ", players=" + players +
                ", version=" + version +
                ", log=" + log +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ClientModel) {
            return equals((ClientModel) other);
        }
        return false;
    }

    public boolean equals(ClientModel other) {
        return (
                chat == other.chat &&
                        winner == other.winner &&
                        turnTracker == other.turnTracker &&
                        map == other.map &&
                        bank == other.bank &&
                        tradeOffer == other.tradeOffer &&
                        players == other.players &&
                        version == other.version &&
                        log == other.log
        );
    }
}