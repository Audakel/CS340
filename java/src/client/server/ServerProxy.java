package client.server;

import com.sun.istack.internal.NotNull;
import shared.IServer;
import shared.exceptions.JoinGameException;
import shared.models.game.AddAIRequest;
import shared.models.game.ClientModel;
import shared.models.games.*;
import shared.models.moves.*;
import shared.models.user.Credentials;
import shared.models.util.ChangeLogLevelRequest;
import shared.serialization.ModelSerializer;

import javax.naming.CommunicationException;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * Created by elijahgk on 9/12/2016.
 * Implements the IServerProxy Interface.
 * Receives calls from client controllers.  Calls methods in clientCommunicator to communicate with actual server.
 */
public class ServerProxy implements IServer {

    private IClientCommunicator cc;

    public ServerProxy() {
        cc = ClientCommunicator.getSingleton();
    }

    public void setMockCC(ClientModel cm){
        cc = MockCC.initialize(cm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(@NotNull Credentials credentialsObject) throws CredentialNotFoundException{
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject,Credentials.class);
        try {
            cc.sendHTTPRequest("/user/login",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            if(e.getMessage().equals("400")){
                throw new CredentialNotFoundException("Username or password were not recognized.");
            }
            else{
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull Credentials credentialsObject) throws CredentialNotFoundException{
        String requestBody = ModelSerializer.getInstance().toJson(credentialsObject, Credentials.class);
        try {
            cc.sendHTTPRequest("/user/register",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            if(e.getMessage().equals("400")){
                throw new CredentialNotFoundException("Invalid username or password.");
            }
            else{
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo[] listOfGames(){
        GameInfo[] games = null;
        try {
            String list = cc.sendHTTPRequest("/games/list","","GET");
            games = ModelSerializer.getInstance().fromJson(list, GameInfo[].class);
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return games;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(@NotNull CreateGameRequest createGameObject){
        String requestBody = ModelSerializer.getInstance().toJson(createGameObject, CreateGameRequest.class);
        try {
            cc.sendHTTPRequest("/games/create",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void joinGame(@NotNull JoinGameRequest joinGameObject) throws JoinGameException{
        String requestBody = ModelSerializer.getInstance().toJson(joinGameObject, JoinGameRequest.class);
        try {
            cc.sendHTTPRequest("/games/join",requestBody,"POST");
        } catch (IllegalArgumentException e){
            throw new JoinGameException("Player could not join game");
        } catch (CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(@NotNull SaveGameRequest saveGameObject){
        String requestBody = ModelSerializer.getInstance().toJson(saveGameObject, SaveGameRequest.class);
        try {
            cc.sendHTTPRequest("/games/save",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGame(@NotNull LoadGameRequest loadGameObject){
        String requestBody = ModelSerializer.getInstance().toJson(loadGameObject, LoadGameRequest.class);
        try {
            cc.sendHTTPRequest("/games/load",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel gameState(int version){
        String requestBody = ModelSerializer.getInstance().toJson(version, Integer.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/game/model",requestBody,"GET");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel resetGame(){
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/game/reset","","GET");
        } catch (CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCommandsGame(){
        String commands ="";
        try {
            commands = cc.sendHTTPRequest("/game/commands","","GET");
        } catch (CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(commands, String[].class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel postCommandsGame(@NotNull String[] gameCommands){
        String requestBody = ModelSerializer.getInstance().toJson(gameCommands, String[].class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/game/commands",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAI(){
        String list = "";
        try {
            list = cc.sendHTTPRequest("/game/listAI","","GET");
        } catch (CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(list,String[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAI(@NotNull AddAIRequest addAIObject){
        String requestBody = ModelSerializer.getInstance().toJson(addAIObject, AddAIRequest.class);
        try {
            cc.sendHTTPRequest("/game/addAI",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLogLevel(@NotNull ChangeLogLevelRequest changeLogLevelObject){
        String requestBody = ModelSerializer.getInstance().toJson(changeLogLevelObject, ChangeLogLevelRequest.class);
        try {
            cc.sendHTTPRequest("/util/changeLogLevel",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel sendChat(@NotNull SendChatAction sendChatObject){
        String requestBody = ModelSerializer.getInstance().toJson(sendChatObject, SendChatAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/sendChat",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel acceptTrade(@NotNull AcceptTradeAction acceptTradeObject){
        String requestBody = ModelSerializer.getInstance().toJson(acceptTradeObject, AcceptTradeAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/acceptTrade",requestBody,"POST");
            return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel discardCards(@NotNull DiscardCardsAction discardCardsObject){
        String requestBody = ModelSerializer.getInstance().toJson(discardCardsObject, DiscardCardsAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/discardCards",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel rollNumber(@NotNull RollNumberAction rollNumberObject){
        String requestBody = ModelSerializer.getInstance().toJson(rollNumberObject, RollNumberAction.class);
        String modelJson = null;
        try {
            modelJson = cc.sendHTTPRequest("/moves/rollNumber",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildRoad(@NotNull BuildRoadAction buildRoadObject){
        String requestBody = ModelSerializer.getInstance().toJson(buildRoadObject, BuildRoadAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/buildRoad",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildSettlement(@NotNull BuildSettlementAction buildSettlementObject){
        String requestBody = ModelSerializer.getInstance().toJson(buildSettlementObject, BuildSettlementAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/buildSettlement",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buildCity(@NotNull BuildCityAction buildCityObject){
        String requestBody = ModelSerializer.getInstance().toJson(buildCityObject, BuildCityAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/buildCity",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel offerTrade(@NotNull OfferTradeAction offerTradeObject){
        String requestBody = ModelSerializer.getInstance().toJson(offerTradeObject, OfferTradeAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/offerTrade",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel maritimeTrade(@NotNull MaritimeTradeAction maritimeTradeObject){
        String requestBody = ModelSerializer.getInstance().toJson(maritimeTradeObject, MaritimeTradeAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/maritimeTrade",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel robPlayer(@NotNull RobPlayerAction robPlayerObject){
        String requestBody = ModelSerializer.getInstance().toJson(robPlayerObject, RobPlayerAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/robPlayer",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel finishTurn(@NotNull FinishMoveAction finishMoveObject){
        String requestBody = ModelSerializer.getInstance().toJson(finishMoveObject, FinishMoveAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/finishTurn",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel buyDevCard(@NotNull BuyDevCardAction buyDevCardObject){
        String requestBody = ModelSerializer.getInstance().toJson(buyDevCardObject, BuyDevCardAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/buyDevCard",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useSoldier(@NotNull SoldierAction soldierObject){
        String requestBody = ModelSerializer.getInstance().toJson(soldierObject, SoldierAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/Soldier",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useYearOfPlenty(@NotNull YearofPlentyAction yearOfPlentyObject){
        String requestBody = ModelSerializer.getInstance().toJson(yearOfPlentyObject, YearofPlentyAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/Year_Of_Plenty",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useRoadBuilding(@NotNull RoadBuildingAction roadBuildingObject){
        String requestBody = ModelSerializer.getInstance().toJson(roadBuildingObject, RoadBuildingAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/Road_Building",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonopoly(@NotNull MonopolyAction monopolyObject){
        String requestBody = ModelSerializer.getInstance().toJson(monopolyObject, MonopolyAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/Monopoly",requestBody,"POST");
        } catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientModel useMonument(@NotNull MonumentAction monumentObject){
        String requestBody = ModelSerializer.getInstance().toJson(monumentObject, MonumentAction.class);
        String modelJson = "";
        try {
            modelJson = cc.sendHTTPRequest("/moves/Monument",requestBody,"POST");
        }catch (IllegalArgumentException | CommunicationException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ModelSerializer.getInstance().fromJson(modelJson,ClientModel.class);
    }
}
