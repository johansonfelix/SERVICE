package rest;

import impl.Albums;
import org.json.JSONObject;
import pojo.Album;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("album")
public class AlbumRest {

    private static Albums albums = new Albums();
    //change return type
    @POST
    @Path("add")
    @Consumes(MediaType.TEXT_XML)
    public String addAlbum(Album album) {

        //check if album ISRC already exists
        //if ISRC exists then return error - album ISRC already exists

        if(albums.getAlbum(album.getISRC()) != null){
            return "An album with that ISRC already exists. Please try entering a different one.";
        }
//        //else add album and return success message
        albums.addAlbum(album);
        return "Successfully added album: [" + album.toString()+"]";
    }
    @PUT
    @Path("update-single-attribute/{ISRC}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAttribute(@PathParam("ISRC") String ISRC, String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        String attribute = json.getString("attribute");
        String newValue = json.getString("newValue");
        System.out.println("ISRC " + ISRC);
        System.out.println("Attribute " + attribute);
        System.out.println("New value " + newValue);
        System.out.println("Reached");
        if (!albums.albumExists(ISRC))
            return "Album "+attribute+" couldn't be updated. Album does not exist.";
        else {
            switch (attribute) {
                case "title": {
                    albums.getAlbum(ISRC).setTitle(newValue);
                    break;
                }

                case "description": {
                    albums.getAlbum(ISRC).setDescription(newValue);
                    break;
                }

                case "releaseYear": {
                    albums.getAlbum(ISRC).setReleaseYear(newValue);
                    break;
                }

                case "artistNickname": {
                    albums.getAlbum(ISRC).setArtist(newValue);
                }
            }
            return "Album "+attribute+" updated.";
        }
    }
    @PUT
    @Path("update-all-attributes/{ISRC}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAllAttributes(@PathParam("ISRC") String ISRC, String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String newTitle = jsonObject.getString("newTitle");
        String newDescription = jsonObject.getString("newDescription");
        String newReleaseYear = jsonObject.getString("newReleaseYear");
        String newArtistNickname = jsonObject.getString("newArtistNickname");
        if (!albums.albumExists(ISRC)) {
            return "Album couldn't be updated. Album does not exist.";
        }
        else{
            Album albumToUpdate = albums.getAlbum(ISRC);
            albumToUpdate.setTitle(newTitle);
            albumToUpdate.setDescription(newDescription);
            albumToUpdate.setReleaseYear(newReleaseYear);
            albumToUpdate.setArtist(newArtistNickname);
            return "Album updated";
        }
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("get-details/{isrc}")
    public String getAlbum(@PathParam("isrc") String ISRC){
        if(albums.getAlbum(ISRC) == null){
            return "Album not found";
        } else return albums.getAlbum(ISRC).toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("get-details-all")
    public String listAlbums(){
        return albums.toString();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete/{ISRC}")
    public String deleteAlbum(@PathParam("ISRC") String ISRC){
        if(albums.getAlbum(ISRC) == null){
            return "Album doesn't exist";
        } else {
            albums.deleteAlbum(ISRC);
            return "Album deleted";
        }
    }
}
