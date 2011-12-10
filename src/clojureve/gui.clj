(ns clojureve.gui
  (:import [javax.swing
            JFrame
            JPanel
            JDialog
            JLabel
            JTextField
            JButton
	    AbstractAction])
  (:import [java.awt
            BorderLayout
            GridBagLayout
            GridBagConstraints
            Insets
            Dimension]))

(def #^{:private true} user-id-text-input
     (doto (JTextField.)
       (.setMinimumSize (Dimension. 200 27))
       (.setPreferredSize (Dimension. 200 27))))

(def #^{:private true} api-key-text-input
     (doto (JTextField.)
       (.setMinimumSize (Dimension. 200 27))
       (.setPreferredSize (Dimension. 200 27))))

(def #^{:private true} api-key-submit-button
     (JButton. (proxy [AbstractAction] ["SUBMIT"]
		 (actionPerformed [event]
				  ;; TODO store api-key and make call to character list.
				  ))))

(defn get-api-key
  "Pops up a form dialog for User ID and API Key."
  [parent]
  (doto (JDialog. parent "EVE API Key" true)
    (.setLayout (GridBagLayout.))
    (.add (JLabel. "User ID:")
          (let [gbc (GridBagConstraints.)]
            (set! (. gbc gridx) 0)
            (set! (. gbc gridy) 0)
            (set! (. gbc anchor) GridBagConstraints/FIRST_LINE_END)
            (set! (. gbc insets) (Insets. 11 11 0 6))
            gbc))
    (.add user-id-text-input
          (let [gbc (GridBagConstraints.)]
            (set! (. gbc gridx) 1)
            (set! (. gbc gridy) 0)
            (set! (. gbc anchor) GridBagConstraints/FIRST_LINE_START)
            (set! (. gbc insets) (Insets. 11 6 6 11))
            gbc))
    (.add (JLabel. "API Key:")
          (let [gbc (GridBagConstraints.)]
            (set! (. gbc gridx) 0)
            (set! (. gbc gridy) 1)
            (set! (. gbc anchor) GridBagConstraints/FIRST_LINE_END)
            (set! (. gbc insets) (Insets. 0 11 0 6))
	    gbc))
    (.add api-key-text-input
          (let [gbc (GridBagConstraints.)]
            (set! (. gbc gridx) 1)
            (set! (. gbc gridy) 1)
            (set! (. gbc anchor) GridBagConstraints/FIRST_LINE_START)
            (set! (. gbc insets) (Insets. 0 6 6 11))
	    gbc))
    (.add api-key-submit-button
          (let [gbc (GridBagConstraints.)]
            (set! (. gbc gridx) 1)
            (set! (. gbc gridy) 2)
            (set! (. gbc anchor) GridBagConstraints/FIRST_LINE_START)
            (set! (. gbc insets) (Insets. 0 6 11 0))
	    gbc))
    (.pack)
    (.setLocationRelativeTo parent)
    (.setVisible true)))

(def #^{:doc "The content pane for the main window."}
     content-pane
     (doto (JPanel. (BorderLayout.))))

(def #^{:doc "The main window of the application."}
     top-frame
     (doto (JFrame. "ClojurEVE Test UI")
       (.setSize 640 480)
       (.setLocationRelativeTo nil)
       (.setVisible true)))

(get-api-key top-frame)


