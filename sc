-- ==========================================================
-- CELAH HUNTER V3 - REAL-TIME UI STATE WATCHER
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("CelahWatcherUI") then
    PlayerGui.CelahWatcherUI:Destroy()
end

local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "CelahWatcherUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 300, 0, 190)
MainFrame.Position = UDim2.new(0.5, -150, 0.4, -95)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Celah Hunter V3: UI State Watcher"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 11
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local TargetInput = Instance.new("TextBox")
TargetInput.Size = UDim2.new(1, -20, 0, 28)
TargetInput.Position = UDim2.new(0, 10, 0, 32)
TargetInput.BackgroundColor3 = Color3.fromRGB(28, 28, 38)
TargetInput.Text = "Homeless Fisher"
TargetInput.PlaceholderText = "Ketik Nama Target..."
TargetInput.TextColor3 = Color3.fromRGB(255, 255, 255)
TargetInput.Font = Enum.Font.SourceSansSemibold
TargetInput.TextSize = 11
TargetInput.Parent = MainFrame

local InputCorner = Instance.new("UICorner")
InputCorner.CornerRadius = UDim.new(0, 6)
InputCorner.Parent = TargetInput

local StatusBox = Instance.new("TextLabel")
StatusBox.Size = UDim2.new(1, -20, 0, 50)
StatusBox.Position = UDim2.new(0, 10, 0, 68)
StatusBox.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
StatusBox.TextColor3 = Color3.fromRGB(100, 255, 150)
StatusBox.Font = Enum.Font.Code
StatusBox.TextSize = 10
StatusBox.TextXAlignment = Enum.TextXAlignment.Left
StatusBox.TextYAlignment = Enum.TextYAlignment.Top
StatusBox.Text = "Status: Siap memantau teks layar..."
StatusBox.Parent = MainFrame

local BoxCorner = Instance.new("UICorner")
BoxCorner.CornerRadius = UDim.new(0, 6)
BoxCorner.Parent = StatusBox

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 30)
ToggleBtn.Position = UDim2.new(0, 10, 0, 146)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "MULAI PANTAU & ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- Fungsi Mendapatkan ProximityPrompt Roll
local function GetMyRollPrompt()
    local character = LocalPlayer.Character
    if not character or not character:FindFirstChild("HumanoidRootPart") then return nil end
    local myPos = character.HumanoidRootPart.Position

    local closestPrompt = nil
    local shortestDistance = 15

    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollBtn = building:FindFirstChild("RollButton")
            if rollBtn then
                for _, prompt in ipairs(rollBtn:GetDescendants()) do
                    if prompt:IsA("ProximityPrompt") then
                        local part = prompt.Parent
                        if part and part:IsA("BasePart") then
                            local dist = (part.Position - myPos).Magnitude
                            if dist < shortestDistance then
                                shortestDistance = dist
                                closestPrompt = prompt
                            end
                        end
                    end
                end
            end
        end
    end
    return closestPrompt
end

local isWatching = false

ToggleBtn.MouseButton1Click:Connect(function()
    isWatching = not isWatching
    if isWatching then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "HENTIKAN PEMANTAUAN"

        task.spawn(function()
            local targetName = string.lower(string.gsub(TargetInput.Text, "^%s*(.-)%s*$", "%1"))

            while isWatching do
                -- 1. Picu Roll
                local prompt = GetMyRollPrompt()
                if prompt then
                    if fireproximityprompt then
                        fireproximityprompt(prompt)
                    else
                        prompt:InputHoldBegin()
                        task.wait(0.05)
                        prompt:InputHoldEnd()
                    end
                end

                StatusBox.Text = "Status: Meroll... Memindai perubahan UI..."

                -- 2. Pantau perubahan teks di seluruh PlayerGui selama jeda roll
                local startTime = tick()
                local matchedFound = false

                while tick() - startTime < 0.7 and isWatching do
                    for _, gui in ipairs(PlayerGui:GetChildren()) do
                        if gui:IsA("ScreenGui") and gui.Name ~= "CelahWatcherUI" then
                            for _, desc in ipairs(gui:GetDescendants()) do
                                if (desc:IsA("TextLabel") or desc:IsA("TextButton")) and desc.Visible then
                                    local txt = string.lower(desc.Text)
                                    if txt ~= "" and (string.find(txt, targetName, 1, true) or string.find(targetName, txt, 1, true)) then
                                        matchedFound = true
                                        break
                                    end
                                end
                            end
                        end
                        if matchedFound then break end
                    end

                    if matchedFound then break end
                    task.wait(0.05)
                end

                if matchedFound then
                    isWatching = false
                    ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
                    ToggleBtn.Text = "MULAI PANTAU & ROLL"
                    StatusBox.Text = "BERHASIL STOP! TARGET TERDETEKSI DI UI!"
                    break
                end

                task.wait(0.1)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "MULAI PANTAU & ROLL"
        StatusBox.Text = "Status: Berhenti."
    end
end)
