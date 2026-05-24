package tickets;


import org.json.JSONObject;


public class CreateTicketBody {
    public JSONObject getBodyCreateTickets(String title, String description, String attachment, Boolean isPublic) {
        JSONObject body = new JSONObject();
        body.put("title", title);
        body.put("description", description);
        body.put("attachment", attachment);
        body.put("isPublic", isPublic);
        return body;
    }
}
