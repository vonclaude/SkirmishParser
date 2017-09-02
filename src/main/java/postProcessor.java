import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by vonclaude on 8/11/2017.
 */
public class postProcessor {

    public static void main(String [ ] args) throws IOException {
        postProcessor postProcessor = new postProcessor();

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> diceList = new ArrayList<>();
        ArrayList<ArrayList<String>> actionList = new ArrayList<>();
        Document doc = null;
        String threadURL;
        int phaseStart;
        boolean outputAsList = false;
        boolean validThreadInput = false;
        boolean validBooleanInput = false;

        //Ask user for thread URL, check that it exists
        do {
            try {
                System.out.print("Enter the URL of the thread: ");
                Scanner threadURLScanner = new Scanner(System.in);
                threadURL = threadURLScanner.next();
                doc = Jsoup.connect(threadURL)
                        .maxBodySize(0)
                        .timeout(600000)
                        .get();
                validThreadInput = true;

            } catch (UnknownHostException | HttpStatusException | MalformedURLException ex) {
                System.out.println("Invalid URL!");
            }
        } while (!validThreadInput);

        //Ask user for post that starts Action Phase
        Scanner phaseStartScanner = new Scanner(System.in);
        System.out.print("Enter the post number of the start of the action phase: ");
        phaseStart = phaseStartScanner.nextInt();

        //Ask user for full list output or per post output
        do {
            try {
                System.out.print("Type true for the full list of actions, false for one at a time.");
                Scanner postOutputScanner = new Scanner(System.in);
                outputAsList = postOutputScanner.nextBoolean();
                validBooleanInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Input true or false exactly as shown!");
            }
        } while (!validBooleanInput);

        //Parse DOM for list of posts
        Elements postList = doc.select(".post");

        //Output relevant posts
        for (Element post : postList) {
            int postNum = Integer.parseInt(post.attr("id").substring(1));

            //Filters for posts after the beginning of Action Phase
            if (phaseStart < postNum) {
                String name = postProcessor.getName(post);
                String dice = postProcessor.getDice(post);
                ArrayList actions = postProcessor.getActions(post);

                //Filters anonymous posts, posts with no greentext
                if (!name.equals("Anonymous") && !actions.isEmpty()) {
                    nameList.add(name);
                    diceList.add(dice);
                    actionList.add(actions);

                }
            }
        }

        for(int i = 0; i < nameList.size(); i++) {
            ArrayList currentActions = actionList.get(i);

            System.out.println("=====");
            System.out.println(nameList.get(i));

            if(!"".equals(diceList.get(i))) {
                System.out.println(diceList.get(i));
            }

            for (Object currentAction : currentActions) {
                System.out.println(currentAction);
            }

            if(!outputAsList) {
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            } else {
                System.out.println();
            }

        }

        if(outputAsList) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Press Enter to close console.");
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
        ArrayList<String> actionList = new ArrayList<>();
        Elements actions = post.select(".quote");

        for (Element action : actions) {
            actionList.add(action.text());
        }

        return actionList;
    }
}