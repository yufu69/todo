(ns todo.boundary.todos
  (:require [clojure.java.jdbc :as jdbc]))

(defprotocol Todos
  (get-todos [db])
  (create-todo [db params]))

(extend-protocol Todos
  duct.database.sql.Boundary
  
  (get-todos [{:keys [spec]}]
    (jdbc/query spec ["SELECT * FROM todos"]))
  
  (create-todo [{:keys [spec]} params]
    (jdbc/insert! spec :todos {:title (:title params)})))