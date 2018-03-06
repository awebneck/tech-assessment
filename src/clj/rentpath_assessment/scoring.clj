(ns rentpath-assessment.scoring)

(defonce scores {"PushEvent" 5
                 "PullRequestReviewCommentEvent" 4
                 "WatchEvent" 3
                 "CreateEvent" 2})

(defn score [key]
  (get scores (str key) 1))
