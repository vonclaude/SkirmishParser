import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by vonclaude on 8/11/2017.
 */
public class postProcessor {

    public static void main(String [ ] args) throws IOException {
        postProcessor postProcessor = new postProcessor();
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> diceList = new ArrayList<String>();
        ArrayList<ArrayList<String>> actionList = new ArrayList<ArrayList<String>>();

        //Ask user for thread URL
        Scanner threadURLScanner = new Scanner(System.in);
        System.out.println("Enter the URL of the thread: ");
        String threadURL = threadURLScanner.next();

        //Ask user for post that starts Action Phase
        Scanner phaseStartScanner = new Scanner(System.in);
        System.out.print("Enter the post number of the start of the action phase: ");
        int phaseStart = phaseStartScanner.nextInt();

        //Connect to thread, parse HTML for list of posts
        Document doc = Jsoup.connect(threadURL)
                .maxBodySize(0)
                .timeout(600000)
                .get();
        Elements postList = doc.select(".post");

        //Output
        for (Element post : postList) {
            int postNum = Integer.parseInt(post.attr("id").substring(1));

            //Filters for posts after the beginning of Action Phase
            if (phaseStart < postNum) {
                String name = postProcessor.getName(post);
                String dice = postProcessor.getDice(post);
                ArrayList actions = postProcessor.getActions(post);

                //Filters anonymous posts, posts with no Greentext
                if (!name.equals("Anonymous") && !actions.isEmpty()) {
                    nameList.add(name);
                    diceList.add(dice);
                    actionList.add(actions);

                }
            }
        }

        for(int i = 0; i < nameList.size(); i++) {
            ArrayList currentActions = actionList.get(i);

            System.out.println(nameList.get(i));

            if(!"".equals(diceList.get(i))) {
                System.out.println(diceList.get(i));
            }

            for (int j = 0; j < currentActions.size(); j++) {
                System.out.println(currentActions.get(j));
            }

            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();

        }
    }

    private String getName(Element post) {

        return post.select(".name").first().text();
    }

    private String getDice(Element post) {

        return post.select(".postMessage > b").text();
    }

    private ArrayList getActions(Element post) {
        ArrayList<String> actionList = new ArrayList<String>();
        Elements actions = post.select(".quote");

        for (Element action : actions) {
            actionList.add(action.text());
        }

        return actionList;
    }
}